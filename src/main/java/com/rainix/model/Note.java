package com.rainix.model;

public enum Note {
    A("A",60), D("S",62), E("D",64), F("F",65),
    G("G",67), L("H",69), B("J",71), Csh("W",61),
    Dsh("E",63), Fsh("T",66), Gsh5("Y",68), Ash("U",70);

    public String key;
    public int code;

    Note(String key, int code) {
        this.key = key;
        this.code = code;
    }

    public static Note getNoteByKey(String key) {
        for (Note note : Note.values()) {
            if (note.key.equals(key)) return note;
        }
        throw new IllegalArgumentException();
    }

}
