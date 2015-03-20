package com.visa.dao;

import java.util.List;
import java.util.Map;

import com.visa.po.Note;

public interface NoteDao {

	Integer insert(Note note);
    List<Note> selectAll(Map<String, Object> map);
    List<Note> selectBySaleId(Map<String, Object> map);
    Integer count();
}
