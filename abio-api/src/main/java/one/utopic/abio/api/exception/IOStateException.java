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
package one.utopic.abio.api.exception;

/**
 * Signals that a IO handler has been invoked at an illegal or inappropriate
 * time. In other words, the underlying IO device or channel is not in an
 * appropriate state for the requested operation.
 */
public class IOStateException extends IllegalStateException {

	private static final long serialVersionUID = -9183786040321212015L;

	public IOStateException() {
		super();
	}

	public IOStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public IOStateException(String message) {
		super(message);
	}

	public IOStateException(Throwable cause) {
		super(cause);
	}

}
