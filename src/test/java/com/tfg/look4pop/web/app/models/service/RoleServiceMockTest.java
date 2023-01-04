package com.tfg.look4pop.web.app.models.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfg.look4pop.web.app.models.entity.Role;
import com.tfg.look4pop.web.app.models.mapper.IRoleMapper;

@SpringBootTest
public class RoleServiceMockTest {
	
	@Mock
	private IRoleMapper mockRoleMapper;
	
	@InjectMocks // auto inject mockRoleMapper
    private IRoleService roleService = new RoleServiceImpl();
	
	@BeforeEach
	void setUp() {
		//NA
	}	
	
	// Test insert
	@DisplayName("RoleService -> insert")
    @Test
    void testInsert() {
		Date date = new Date();	
		Role role8 = new Role(8, 5, "ROLE_ADMIN", "admin0", new Timestamp(date.getTime()), null, null);
		roleService.insert(role8);
		verify(mockRoleMapper, times(1)).insert(role8);
    }
	
	@AfterEach
    void tearDown() {
    	//NA
    }

}
