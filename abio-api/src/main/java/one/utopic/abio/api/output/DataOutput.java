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
package one.utopic.abio.api.output;

import java.io.IOException;

public interface DataOutput extends Output {

    void writeShort(short s) throws IOException;

    void writeInt(int i) throws IOException;

    void writeLong(long l) throws IOException;

    void writeFloat(float f) throws IOException;

    void writeDouble(double d) throws IOException;

    void writeChar(char c) throws IOException;

    void writeString(String s) throws IOException;

    void writeString(String s, String charsetName) throws IOException;

    void writeBoolean(boolean b) throws IOException;

}
