package com.github.hayataka.prepareForGitExample;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.github.hayataka.prepareForGitExample.entity.TbMemberEntity;
import com.github.hayataka.prepareForGitExample.initializer.DbInitializer;
import com.github.hayataka.prepareForGitExample.mapper.TbMemberMapper;

public class EntryPoint {

	public static void main(String[] args) throws IOException, SQLException {
		

//		try(DbInitializer init = new DbInitializer()) {
//			init.prepare();
//		}

		
		 // ★ルートとなる設定ファイルを読み込む
        try (InputStream in = EntryPoint.class.getResourceAsStream("/mybatis-config.xml")) {
            // ★設定ファイルを元に、 SqlSessionFactory を作成する
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

            // ★SqlSessionFactory から SqlSession を生成する
            try (SqlSession session = factory.openSession()) {
            	
            	
            	TbMemberMapper tbMemberMapper = session.getMapper(TbMemberMapper.class);

         
            	tbMemberMapper.create();
            	
            	for (int i = 0; i < 20; i++) {

    				TbMemberEntity entity = new TbMemberEntity();
    				entity.setName(i + "a");
    				Date birthday = Date.from(LocalDate.now().plusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant());            	
					entity.setBirthday(birthday );
    				
    				tbMemberMapper.insert(entity );
            	}



            	
            	List<TbMemberEntity> list = tbMemberMapper.selectList();
            	list.forEach(x -> {
            		System.out.println(x);
            	});

            }
        }
		
	}
}
