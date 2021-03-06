/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 TRIOLOGY GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.triology.blog.complexspermissions;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PermissionForTheFileItselfTest {

    @Test
    public void impliesAccessToItsOwnFile() throws Exception {
        String filePath = "some/file/path";
        PermissionForTheFileItself permission = new PermissionForTheFileItself(Paths.get(filePath), FileOperation.READ);
        RequestedFileAccess requestedFileAccess = new RequestedFileAccess(Paths.get(filePath), FileOperation.READ);
        assertTrue(permission.implies(requestedFileAccess));
    }

    @Test
    public void deniesAccessToAllFilesOtherThanItsOwn() throws Exception {
        PermissionForTheFileItself permission = new PermissionForTheFileItself(Paths.get("some/file/path"), FileOperation.READ);
        RequestedFileAccess requestedFileAccess = new RequestedFileAccess(Paths.get("other/file/path"), FileOperation.READ);
        assertFalse(permission.implies(requestedFileAccess));
    }

    @Test
    public void passesTheSpecifiedFileOperationToItsBaseClass() throws Exception {
        PermissionForTheFileItself permission = new PermissionForTheFileItself(Paths.get("some/file/path"), FileOperation.READ);
        assertEquals(FileOperation.READ, permission.operation);
    }
}