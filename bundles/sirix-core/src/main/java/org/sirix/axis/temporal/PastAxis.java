package org.sirix.axis.temporal;

import static com.google.common.base.Preconditions.checkNotNull;

import org.sirix.api.NodeReadTrx;
import org.sirix.api.Session;
import org.sirix.axis.AbstractTemporalAxis;
import org.sirix.axis.IncludeSelf;

/**
 * Retrieve a node by node key in all earlier revisions. In each revision a
 * {@link NodeReadTrx} is opened which is moved to the node with the given node
 * key if it exists. Otherwise the iterator has no more elements (the
 * {@link NodeReadTrx} moved to the node by it's node key).
 * 
 * @author Johannes Lichtenberger
 * 
 */
public final class PastAxis extends AbstractTemporalAxis {

	/** The revision number. */
	private int mRevision;

	/** Sirix {@link Session}. */
	private final Session mSession;

	/** Node key to lookup and retrieve. */
	private long mNodeKey;

	/** Sirix {@link NodeReadTrx}. */
	private NodeReadTrx mRtx;

	/**
	 * Constructor.
	 * 
	 * @param rtx
	 *          Sirix {@link NodeReadTrx}
	 */
	public PastAxis(final NodeReadTrx rtx) {
		// Using telescope pattern instead of builder (only one optional parameter.
		this(rtx, IncludeSelf.NO);
	}

	/**
	 * Constructor.
	 * 
	 * @param includeSelf
	 *          determines if current revision must be included or not
	 */
	public PastAxis(final NodeReadTrx rtx, final IncludeSelf includeSelf) {
		mSession = checkNotNull(rtx.getSession());
		mRevision = 0;
		mNodeKey = rtx.getNodeKey();
		mRevision = checkNotNull(includeSelf) == IncludeSelf.YES ? rtx
				.getRevisionNumber() : rtx.getRevisionNumber() - 1;
	}

	@Override
	protected NodeReadTrx computeNext() {
		if (mRevision > 0) {
			mRtx = mSession.beginNodeReadTrx(mRevision--);
			return mRtx.moveTo(mNodeKey).hasMoved() ? mRtx : endOfData();
		} else {
			return endOfData();
		}
	}

	@Override
	public NodeReadTrx getTrx() {
		return mRtx;
	}
}
