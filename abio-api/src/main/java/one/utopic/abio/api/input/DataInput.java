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
package one.utopic.abio.api.input;

import java.io.IOException;

public interface DataInput extends Input {

    short readShort() throws IOException;

    int readInt() throws IOException;

    long readLong() throws IOException;

    float readFloat() throws IOException;

    double readDouble() throws IOException;

    char readChar() throws IOException;

    String readString() throws IOException;

    String readString(String charsetName) throws IOException;

    boolean readBoolean() throws IOException;
}
