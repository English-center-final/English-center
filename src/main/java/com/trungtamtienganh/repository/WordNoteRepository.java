package com.trungtamtienganh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trungtamtienganh.entity.WordNote;
import com.trungtamtienganh.entity.WordNote_PK;


public interface WordNoteRepository extends JpaRepository<WordNote, WordNote_PK> {

}

