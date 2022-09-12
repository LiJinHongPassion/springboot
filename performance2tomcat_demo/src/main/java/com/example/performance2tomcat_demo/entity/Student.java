package com.example.performance2tomcat_demo.entity;

import org.openjdk.jol.info.ClassLayout;

/**
 * 对象头：
 *  8byte（32位）
 *  16byte（64位）
 *
 *  com.example.performance2tomcat_demo.entity.Student object internals:
 *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           48 72 06 00 (01001000 01110010 00000110 00000000) (422472)
 *      12     4        (alignment/padding gap)
 *      16     8   long Student.age                               0
 * Instance size: 24 bytes
 * Space losses: 4 bytes internal + 0 bytes external = 4 bytes total
 */
public class Student {
    public long age;
}
