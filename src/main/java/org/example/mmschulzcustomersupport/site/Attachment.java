package org.example.mmschulzcustomersupport.site;

import java.util.Arrays;

public class Attachment {

    private static String name;
    private static byte[] contents;

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
