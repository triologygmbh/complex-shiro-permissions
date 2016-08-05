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

import org.apache.shiro.authz.permission.WildcardPermission;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BaseFilePermissionTest {

    private Path file = Paths.get("");
    private boolean accessAllowedBySubclass;

    @Before
    public void setUp() throws Exception {
        accessAllowedBySubclass = true;
    }

    @Test
    public void impliesRequestedFileAccessPermissions() throws Exception {
        TestFilePermission filePermission = new TestFilePermission(file, FileOperation.WRITE);
        RequestedFileAccess requestedFileAccess = new RequestedFileAccess(file, FileOperation.READ);
        assertTrue(filePermission.implies(requestedFileAccess));
    }

    @Test
    public void doesNotImplyPermissionsOtherThanRequestedFileAccess() throws Exception {
        TestFilePermission filePermission = new TestFilePermission(file, FileOperation.READ);
        assertFalse(filePermission.implies(new WildcardPermission("some:permission")));
    }

    @Test
    public void impliesRequestedReadPermissionWhenItsOwnOperationIsRead() throws Exception {
        TestFilePermission filePermission = new TestFilePermission(file, FileOperation.READ);
        RequestedFileAccess requestedFileAccess = new RequestedFileAccess(file, FileOperation.READ);
        assertTrue(filePermission.implies(requestedFileAccess));
    }

    @Test
    public void impliesRequestedReadPermissionWhenItsOwnOperationIsWrite() throws Exception {
        TestFilePermission filePermission = new TestFilePermission(file, FileOperation.READ);
        RequestedFileAccess requestedFileAccess = new RequestedFileAccess(file, FileOperation.READ);
        assertTrue(filePermission.implies(requestedFileAccess));
    }

    @Test
    public void impliesRequestedWritePermissionWhenItsOwnOperationIsWrite() throws Exception {
        TestFilePermission filePermission = new TestFilePermission(file, FileOperation.WRITE);
        RequestedFileAccess requestedFileAccess = new RequestedFileAccess(file, FileOperation.WRITE);
        assertTrue(filePermission.implies(requestedFileAccess));
    }

    @Test
    public void doesNotImplyRequestedWritePermissionWhenItsOwnOperationIsRead() throws Exception {
        TestFilePermission filePermission = new TestFilePermission(file, FileOperation.READ);
        RequestedFileAccess requestedFileAccess = new RequestedFileAccess(file, FileOperation.WRITE);
        assertFalse(filePermission.implies(requestedFileAccess));
    }

    @Test
    public void impliesRequestedFileAccessWhenSubclassAllowesIt() throws Exception {
        TestFilePermission filePermission = new TestFilePermission(file, FileOperation.READ);
        RequestedFileAccess requestedFileAccess = new RequestedFileAccess(file, FileOperation.READ);
        accessAllowedBySubclass = true;
        assertTrue(filePermission.implies(requestedFileAccess));
    }

    @Test
    public void doesNotImplyRequestedFileAccessWhenSubclassDoesNotAllowIt() throws Exception {
        TestFilePermission filePermission = new TestFilePermission(file, FileOperation.READ);
        RequestedFileAccess requestedFileAccess = new RequestedFileAccess(file, FileOperation.READ);
        accessAllowedBySubclass = false;
        assertFalse(filePermission.implies(requestedFileAccess));
    }

    private class TestFilePermission extends BaseFilePermission {

        public TestFilePermission(Path file, FileOperation operation) {
            super(file, operation);
        }

        @Override
        protected boolean accessIsAllowed(Path requestedFile) {
            return accessAllowedBySubclass;
        }
    }
}