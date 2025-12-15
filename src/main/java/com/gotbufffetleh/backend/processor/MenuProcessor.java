package com.gotbufffetleh.backend.processor;


import com.gotbufffetleh.backend.dbTables.Menu;
import com.gotbufffetleh.backend.dto.GetMenuDTO;
import com.gotbufffetleh.backend.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuProcessor {
    private final MenuRepository menuRepository;


    MenuProcessor(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<GetMenuDTO> findMenusByCatererId(Long catererId) {
        List<Menu> menus = this.menuRepository.findMenuByCatererId(catererId);

        return getMenuDTOList(menus);
    }


    //helper method to get menuList
    private List<GetMenuDTO> getMenuDTOList(List<Menu> menuList){
        List<GetMenuDTO> dtoList = new ArrayList<>();

        for (Menu menu : menuList) {
            GetMenuDTO dto = new GetMenuDTO();
            dto.setMenuId(menu.getMenuId());
            dto.setMenuName(menu.getMenuName());
            dto.setMenuItems(menu.getMenuItems());
            dto.setCostPerPax(menu.getCostPerPax());
            dto.setMinimumPax(menu.getMinimumPax());
            dto.setNumOfCourses(menu.getNoOfCourses());

            dtoList.add(dto);
        }
        return dtoList;

    }
}
