# complex-shiro-permissions
A demonstration on how to implement complex permissions with Apache Shiro.

Read the tutorial here: [Apache Shiro: Implementing complex permissions autonomously](https://www.triology.de/en/blog-entries/apache-shiro).

## Contents
This Maven project shows how to realize complex permissions by providing custom implementations of Apache Shiro's `Permission` interface.
It uses the follwing permissions to access files in a tree as an example:
* `de.triology.blog.complexspermissions.PermissionForTheFileItself` - grants access to a particular file
* `de.triology.blog.complexspermissions.ReadPermissionForAncestors` - grants access to a file's ancestors
* `de.triology.blog.complexspermissions.PermissionForAllDescendantFiles` - grants access to a file's descendants

Permissions are configured and assigend to the Shiro `Subject` in `de.triology.blog.complexspermissions.ComplexPermissionRealm`. 

A Demonstration is provided in `de.triology.blog.complexspermissions.demo.Demo`.
