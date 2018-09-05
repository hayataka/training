package com.github.hayataka.prepareForGitExample.mapper;

import java.util.List;

import com.github.hayataka.prepareForGitExample.entity.TbMemberEntity;

public interface TbMemberMapper {

	int create();
	List<TbMemberEntity> selectList();
	int insert(TbMemberEntity entity);
}
