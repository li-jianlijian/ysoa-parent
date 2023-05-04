package com.ys.oa;

import com.ys.oa.model.system.SysRole;
import com.ys.oa.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleServiceTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void getAll(){
        List<SysRole> list = sysRoleService.list();
        System.out.println(list);
    }
}
