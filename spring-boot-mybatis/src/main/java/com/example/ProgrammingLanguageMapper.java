package com.example;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProgrammingLanguageMapper {

	@Delete("""
			TRUNCATE programming_language
			""")
	void deleteAll();

	@Insert("""
			INSERT INTO programming_language(pl_name, pl_rating)
			VALUES(#{name}, #{rating})
			""")
	void insert(@Param("name") String name, @Param("rating") Integer rating);

	@Select("""
			SELECT pl_name, pl_rating
			FROM programming_language
			WHERE pl_rating > #{rating}
			""")
	List<ProgrammingLanguage> findTopProgrammingLanguages(@Param("rating") int rating);

}
