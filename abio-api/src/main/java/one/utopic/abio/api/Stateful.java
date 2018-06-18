/**
 * Copyright © 2017 Anton Filatov (ya-enot@mail.ru)
 *
 * This file is part of ABIO.
 *
 * ABIO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ABIO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ABIO.  If not, see <https://www.gnu.org/licenses/lgpl-3.0>.
 */
package one.utopic.abio.api;

import java.io.EOFException;

import one.utopic.abio.api.exception.IOStateException;

public interface Stateful {

	interface StateController {

		boolean isTransitionAllowed(State fromState, State toState) throws IOStateException;

	}

	interface State {

		String getName();

	}

	State getState();

	boolean isOpened();

	boolean isReady();

	boolean isBusy();

	boolean isFinished();

	boolean isClosed();

	boolean isReleased();

	boolean isError();

	class ResourceStateController implements StateController {

		public boolean isTransitionAllowed(State stateFrom, State stateTo) throws IOStateException {
			if (stateFrom instanceof ResourceState && stateTo instanceof ResourceState) {
				if (ResourceState.ERROR.equals(stateTo)) {
					return true;
				}
				switch ((ResourceState) stateFrom) {
				case CREATED:
					switch ((ResourceState) stateTo) {
					case OPENING:
					case OPENED:
					case READY:
						return true;
					// $CASES-OMITTED$
					default:
						return false;
					}
				case OPENING:
					switch ((ResourceState) stateTo) {
					case OPENED:
					case READY:
						return true;
					// $CASES-OMITTED$
					default:
						return false;
					}
				case OPENED:
					switch ((ResourceState) stateTo) {
					case READY:
						return true;
					// $CASES-OMITTED$
					default:
						return false;
					}
				case READY:
					switch ((ResourceState) stateTo) {
					case PROCESSING:
					case FINISHED:
					case CLOSING:
					case CLOSED:
						return true;
					// $CASES-OMITTED$
					default:
						return false;
					}
				case PROCESSING:
					switch ((ResourceState) stateTo) {
					case READY:
					case FINISHED:
					case CLOSING:
					case CLOSED:
						return true;
					// $CASES-OMITTED$
					default:
						return false;
					}
				case FINISHED:
					switch ((ResourceState) stateTo) {
					case READY:
					case CLOSING:
					case CLOSED:
						return true;
					// $CASES-OMITTED$
					default:
						return false;
					}
				case CLOSING:
					switch ((ResourceState) stateTo) {
					case CLOSED:
						return true;
					// $CASES-OMITTED$
					default:
						return false;
					}
				case CLOSED:
				case ERROR:
					switch ((ResourceState) stateTo) {
					case DESTROYED:
						return true;
					// $CASES-OMITTED$
					default:
						return false;
					}
				case DESTROYED:
				default:
					return false;
				}
			}
			throw new IOStateException("Unsupported state transition from " + stateFrom + " to " + stateTo);
		}

	}

	/**
	 * <h1>State table</h1>
	 * <p>
	 * <blockquote>
	 * <table border="1" cellspacing="0" cellpadding="4">
	 * <th>
	 * <td>{@link #isOpened() isOpened}</td>
	 * <td>{@link #isReady() isReady}</td>
	 * <td>{@link #isBusy() isBusy}</td>
	 * <td>{@link #isFinished() isFinished}</td>
	 * <td>{@link #isClosed() isClosed}</td>
	 * <td>{@link #isReleased() isReleased}</td>
	 * <td>{@link #isError() isError}</td></th>
	 * <tr>
	 * <td>{@link ResourceState#CREATED CREATED}</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>{@link ResourceState#OPENING OPENING}</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>{@link ResourceState#OPENED OPENED}</td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>{@link ResourceState#READY READY}</td>
	 * <td><b>True</b></td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>{@link ResourceState#PROCESSING PROCESSING}</td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>{@link ResourceState#FINISHED FINISHED}</td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>{@link ResourceState#CLOSING CLOSING}</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td><b>True</b></td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>{@link ResourceState#CLOSED CLOSED}</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td><b>True</b></td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>{@link ResourceState#DESTROYED DESTROYED}</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td>False</td>
	 * <td><b>True</b></td>
	 * <td>False</td>
	 * </tr>
	 * <tr>
	 * <td>{@link ResourceState#ERROR ERROR}</td>
	 * <td>?</td>
	 * <td>?</td>
	 * <td>?</td>
	 * <td>?</td>
	 * <td>?</td>
	 * <td>?</td>
	 * <td><b>True</b></td>
	 * </tr>
	 * </table>
	 * </blockquote>
	 * </p>
	 *
	 */
	enum ResourceState implements Stateful, Stateful.State {
		/**
		 * Resource is just created <br/>
		 * <b>Next:</b> {@link #OPENING}, {@link #OPENED}, {@link #READY}
		 */
		CREATED(StateMask.NONE),

		/**
		 * Resource opening in progress <br/>
		 * <b>Next:</b> {@link #OPENED}, {@link #READY}
		 */
		OPENING(StateMask.BUSY),

		/**
		 * Resource is opened successfully <br/>
		 * <b>Next:</b> {@link #READY}
		 */
		OPENED(StateMask.OPENED),

		/**
		 * Resource is ready for processing <br/>
		 * <b>Next:</b> {@link #PROCESSING}, {@link #FINISHED}, {@link #CLOSING},
		 * {@link #CLOSED}
		 */
		READY(StateMask.OPENED & StateMask.READY),

		/**
		 * Resource is waiting for IO operations to be completed <br/>
		 * <b>Next:</b> {@link #READY}, {@link #FINISHED}, {@link #CLOSING},
		 * {@link #CLOSED}
		 */
		PROCESSING(StateMask.OPENED & StateMask.BUSY),

		/**
		 * Resource is at its logical end. <br/>
		 * Any IO attempt in this state should throw an {@link EOFException} <br/>
		 * <b>Next:</b> {@link #READY}, {@link #CLOSING}, {@link #CLOSED}
		 */
		FINISHED(StateMask.OPENED & StateMask.FINISHED),

		/**
		 * Resource closing in progress <br/>
		 * <b>Next:</b> {@link #CLOSED}
		 */
		CLOSING(StateMask.BUSY & StateMask.FINISHED),

		/**
		 * Resource is closed successfully <br/>
		 * <b>Next:</b> {@link #DESTROYED}
		 */
		CLOSED(StateMask.FINISHED & StateMask.CLOSED),

		/**
		 * Resource is released and ready for being garbage collected <br/>
		 */
		DESTROYED(StateMask.RELEASED),

		/**
		 * Resource is broken and/or its state could not be determined <br/>
		 * <b>Next:</b> {@link #DESTROYED}
		 */
		ERROR(StateMask.ERROR);

		private static final class StateMask {
			private static final int NONE = 0;
			private static final int OPENED = 1 << 1;
			private static final int READY = 1 << 2;
			private static final int BUSY = 1 << 3;
			private static final int FINISHED = 1 << 4;
			private static final int CLOSED = 1 << 5;
			public static final int RELEASED = Integer.MIN_VALUE >>> 1;
			private static final int ERROR = Integer.MIN_VALUE;
		}

		private final int states;

		private ResourceState(int states) {
			this.states = states;
		}

		public String getName() {
			return name();
		}

		public State getState() {
			return this;
		}

		public boolean isOpened() {
			return (states & StateMask.OPENED) != 0;
		}

		public boolean isReady() {
			return (states & StateMask.READY) != 0;
		}

		public boolean isBusy() {
			return (states & StateMask.BUSY) != 0;
		}

		public boolean isFinished() {
			return (states & StateMask.FINISHED) != 0;
		}

		public boolean isClosed() {
			return (states & StateMask.CLOSED) != 0;
		}

		public boolean isReleased() {
			return (states & StateMask.RELEASED) != 0;
		}

		public boolean isError() {
			return (states & StateMask.ERROR) != 0;
		}
	}
}
