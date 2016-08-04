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

import org.apache.shiro.authz.Permission;

import java.io.IOException;
import java.nio.file.Path;

abstract class BaseFilePermission implements Permission {

    protected final Path file;
    protected final FileOperation operation;

    public BaseFilePermission(Path file, FileOperation operation) {
        this.file = file;
        this.operation = operation;
    }

    @Override
    final public boolean implies(Permission permission) {
        try {
            return isImplied(permission);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isImplied(Permission permission) throws IOException {
        return isFileAccessRequest(permission) && fileAccessIsImplied((RequestedFileAccess) permission);
    }

    private boolean isFileAccessRequest(Permission permission) {
        return permission instanceof RequestedFileAccess;
    }

    private boolean fileAccessIsImplied(RequestedFileAccess requestedFileAccess) throws IOException {
        return requestedOperationIsImplied(requestedFileAccess) && accessIsAllowed(requestedFileAccess);
    }

    private boolean requestedOperationIsImplied(RequestedFileAccess requestedFileAccess) {
        return requestsRead(requestedFileAccess) || writeIsPermitted();
    }

    private boolean requestsRead(RequestedFileAccess requestedFileAccess) {
        return requestedFileAccess.getOperation() == FileOperation.READ;
    }

    private boolean writeIsPermitted() {
        return operation == FileOperation.WRITE;
    }

    /**
     * Template method called during permission check that allows subclasses to check access permissions.
     *
     * @param requestedFileAccess RequestedFileAccess
     * @return true if the RequestedFileAccess is granted
     */
    protected abstract boolean accessIsAllowed(RequestedFileAccess requestedFileAccess) throws IOException;
}
