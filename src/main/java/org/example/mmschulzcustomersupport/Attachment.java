package org.example.mmschulzcustomersupport;

import java.util.Arrays;

public class Attachment {

    public static String name;
    public static byte[] contents;

    public static String getName() {
        return name;
    }

    public static byte[] getContents() {
        return contents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }
    @Override
    public String toString() {
        return "Attachments{" +
                "name='" + name + '\'' +
                ", contents=" + Arrays.toString(contents) +
                '}';
    }
}
