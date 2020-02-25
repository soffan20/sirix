/*
 * [New BSD License]
 * Copyright (c) 2011-2012, Brackit Project Team <info@brackit.org>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Brackit Project Team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.sirix.xquery;

import org.brackit.xquery.QueryContext;
import org.brackit.xquery.QueryException;
import org.brackit.xquery.XQuery;
import org.brackit.xquery.node.parser.DocumentParser;
import org.brackit.xquery.util.io.URIHandler;
import org.brackit.xquery.xdm.node.Node;
import org.brackit.xquery.xdm.node.NodeStore;
import org.sirix.io.StorageType;
import org.sirix.xquery.node.BasicXmlDBStore;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Sebastian Baechle
 * @author Johannes Lichtenberger
 */
public final class Main {

  private static class Config {
    final Map<String, String> options = new HashMap<>();

    boolean isSet(final String option) {
      return options.containsKey(option);
    }

    String getValue(final String option) {
      return options.get(option);
    }

    void setOption(final String option, final String value) {
      options.put(option, value);
    }
  }

  private static class Option {
    final String key;
    final String desc;
    final boolean hasValue;

    Option(final String key, final String desc, final boolean hasValue) {
      this.key = key;
      this.desc = desc;
      this.hasValue = hasValue;
    }
  }

  private static final List<Option> options = new ArrayList<>();

  static {
    options.add(new Option("-q", "query file [use '-' for stdin (default)]", true));
    options.add(new Option("-f", "default document", true));
    options.add(new Option("-p", "pretty print", false));
    options.add(new Option("-m", "use the memory mapped storage backend", false));
  }

  public static void main(final String[] args) {
    try {
      final Config config = parseParams(args);

      var storageType = StorageType.FILE;

      if (config.isSet("-m")) {
        storageType = StorageType.MEMORY_MAPPED_FILE;
      }

      try (final BasicXmlDBStore store = BasicXmlDBStore
          .newBuilder()
          .storageType(storageType)
          .build()) {
        final QueryContext ctx = SirixQueryContext.createWithNodeStore(store);

        final String file = config.getValue("-f");
        if (file != null) {
          var doc = readDocumentFromFile(file, store);
          ctx.setContextItem(doc);
        }

        var query = readQuery(config);
        final XQuery xq = new XQuery(SirixCompileChain.createWithNodeStore(store), query);
        if (config.isSet("-p")) {
          xq.prettyPrint();
        }
        xq.serialize(ctx, System.out);
      }
    } catch (final QueryException e) {
      System.out.println("Error: " + e.getMessage());
      System.exit(-2);
    } catch (final Throwable e) {
      System.out.println("Error: " + e.getMessage());
      System.exit(-4);
    }
  }

  /**
   * Parses a document from a file and returns the Node corresponding to said document.
   *
   * @param file  The file containing a document
   * @param store The NodeStore used to create the NodeCollection for the document
   * @return the parsed Node document
   */
  private static Node<?> readDocumentFromFile(String file, NodeStore store) {
    final URI uri;
    Node<?> doc = null;
    try {
      uri = new URI(file);
      try (InputStream in = URIHandler.getInputStream(uri)) {
        var parser = new DocumentParser(in);
        var name = uri.toURL().getFile();
        var coll = store.create(name, parser);
        doc = coll.getDocument();
      }
    } catch (URISyntaxException e) {
      System.out.println("Specified file path with '-f' is malformed: " + file);
      System.out.println("URI Syntax error: " + e.getMessage());
      System.exit(-5);
    } catch (IOException e) {
      System.out.println("I/O Error: " + e.getMessage());
      System.exit(-3);
    }
    return doc;
  }

  /**
   * Returns a query from a file or stdin depending on the value of the flag `-q` in the config.
   *
   * @param config Contains the option `-q` with its corresponding value specifying the file path or stdin if the value is '-'
   * @return The parsed query
   */
  private static String readQuery(final Config config) {
    String query = "";
    try {
      if (config.isSet("-q") && !"-".equals(config.getValue("-q"))) {
        query = readFile(config.getValue("-q"));
      } else {
        query = readStringFromScanner(System.in);
      }
    } catch (IOException e) {
      System.out.println("I/O Error: " + e.getMessage());
      System.exit(-3);
    }
    return query;
  }

  private static String readStringFromScanner(final InputStream in) {
    try (final Scanner scanner = new Scanner(in)) {
      final StringBuilder strbuf = new StringBuilder();

      while (scanner.hasNextLine()) {
        final String line = scanner.nextLine();

        if (line.trim().isEmpty())
          break;

        strbuf.append(line);
      }

      return strbuf.toString();
    }
  }

  private static Config parseParams(final String[] args) throws Exception {
    final Config config = new Config();
    for (int i = 0; i < args.length; i++) {
      boolean valid = false;
      final String s = args[i];
      for (final Option o : options) {
        if (o.key.equals(s)) {
          final String val = (o.hasValue)
              ? args[++i]
              : null;
          config.setOption(o.key, val);
          valid = true;
          break;
        }
      }
      if (!valid) {
        printUsage();
        throw new Exception("Invalid parameter: " + s);
      }
    }
    return config;
  }

  private static String readFile(final String file) throws IOException {
    try (FileInputStream fin = new FileInputStream(file)) {
      return readString(fin);
    }
  }

  private static String readString(final InputStream in) throws IOException {
    int r;
    final ByteArrayOutputStream payload = new ByteArrayOutputStream();
    while ((r = in.read()) != -1) {
      payload.write(r);
    }
    return payload.toString(StandardCharsets.UTF_8.toString());
  }

  private static void printUsage() {
    System.out.println("No query provided");
    System.out.println(String.format("Usage: java %s [options]", Main.class.getName()));
    System.out.println("Options:");
    for (final Option o : options) {
      System.out.print(" ");
      System.out.print(o.key);
      System.out.print(o.hasValue ? " <param>" : "\t");
      System.out.print("\t- ");
      System.out.println(o.desc);
    }
    System.exit(-1);
  }
}
