package org.sirix.node.immutable;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.annotation.Nullable;

import org.brackit.xquery.atomic.QNm;
import org.sirix.api.visitor.VisitResult;
import org.sirix.api.visitor.Visitor;
import org.sirix.node.Kind;
import org.sirix.node.PINode;
import org.sirix.node.SirixDeweyID;
import org.sirix.node.interfaces.Node;
import org.sirix.node.interfaces.immutable.ImmutableNameNode;
import org.sirix.node.interfaces.immutable.ImmutableStructNode;
import org.sirix.node.interfaces.immutable.ImmutableValueNode;

/**
 * Immutable processing instruction node wrapper.
 * 
 * @author Johannes Lichtenberger
 * 
 */
public class ImmutablePI implements ImmutableValueNode, ImmutableNameNode,
		ImmutableStructNode {
	/** Mutable {@link PINode}. */
	private final PINode mNode;

	/**
	 * Private constructor.
	 * 
	 * @param node
	 *          {@link PINode} to wrap
	 */
	private ImmutablePI(final PINode node) {
		mNode = checkNotNull(node);
	}

	/**
	 * Get an immutable processing instruction node instance.
	 * 
	 * @param node
	 *          the mutable {@link PINode} to wrap
	 * @return immutable processing instruction node instance
	 */
	public static ImmutablePI of(final PINode node) {
		return new ImmutablePI(node);
	}

	@Override
	public int getTypeKey() {
		return mNode.getTypeKey();
	}

	@Override
	public boolean isSameItem(final @Nullable Node other) {
		return mNode.isSameItem(other);
	}

	@Override
	public VisitResult acceptVisitor(final Visitor pVisitor) {
		return pVisitor.visit(this);
	}

	@Override
	public long getHash() {
		return mNode.getHash();
	}

	@Override
	public long getParentKey() {
		return mNode.getParentKey();
	}

	@Override
	public boolean hasParent() {
		return mNode.hasParent();
	}

	@Override
	public long getNodeKey() {
		return mNode.getNodeKey();
	}

	@Override
	public Kind getKind() {
		return mNode.getKind();
	}

	@Override
	public long getRevision() {
		return mNode.getRevision();
	}

	@Override
	public boolean hasFirstChild() {
		return mNode.hasFirstChild();
	}

	@Override
	public boolean hasLeftSibling() {
		return mNode.hasLeftSibling();
	}

	@Override
	public boolean hasRightSibling() {
		return mNode.hasRightSibling();
	}

	@Override
	public long getChildCount() {
		return mNode.getChildCount();
	}

	@Override
	public long getDescendantCount() {
		return mNode.getDescendantCount();
	}

	@Override
	public long getFirstChildKey() {
		return mNode.getFirstChildKey();
	}

	@Override
	public long getLeftSiblingKey() {
		return mNode.getLeftSiblingKey();
	}

	@Override
	public long getRightSiblingKey() {
		return mNode.getRightSiblingKey();
	}

	@Override
	public int getLocalNameKey() {
		return mNode.getLocalNameKey();
	}

	@Override
	public int getPrefixKey() {
		return mNode.getPrefixKey();
	}

	@Override
	public int getURIKey() {
		return mNode.getURIKey();
	}

	@Override
	public long getPathNodeKey() {
		return mNode.getPathNodeKey();
	}

	@Override
	public byte[] getRawValue() {
		return mNode.getRawValue();
	}

	@Override
	public Optional<SirixDeweyID> getDeweyID() {
		return mNode.getDeweyID();
	}

	@Override
	public boolean equals(Object obj) {
		return mNode.equals(obj);
	}

	@Override
	public int hashCode() {
		return mNode.hashCode();
	}

	@Override
	public String toString() {
		return mNode.toString();
	}

	@Override
	public QNm getName() {
		return mNode.getName();
	}

	@Override
	public String getValue() {
		return mNode.getValue();
	}
}
