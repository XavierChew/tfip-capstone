package com.gotbufffetleh.backend.processor;


import com.gotbufffetleh.backend.dbTables.Menu;
import com.gotbufffetleh.backend.dto.GetMenuDTO;
import com.gotbufffetleh.backend.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    // helper method to get paginated view for menu
    // search page (no filters) and if no menu params provided
    public List<GetMenuDTO> getMenusForPaginated(Long catererId){
        List<GetMenuDTO> dtoList = new ArrayList<>();

        for(Menu menu : this.menuRepository.findMenuByCatererId(catererId)){
            mapToGetMenuDTO(dtoList, menu);
        }
        return dtoList;
    }

    // all caterers page - method overloading based on params provided
    // if only noOfPax provided
    public List<GetMenuDTO> getMenusForPaginated(Long catererId, int noOfPax) {
        List<GetMenuDTO> dtoList = new ArrayList<>();
        for(Menu menu : this.menuRepository.findMenuByCatererId(catererId)) {
            if (noOfPax >= menu.getMinimumPax()) {
                mapToGetMenuDTO(dtoList, menu);
            }
        }
        return dtoList;
    }

    // if only budget provided
    public List<GetMenuDTO> getMenusForPaginated(Long catererId, BigDecimal budget) {
        List<GetMenuDTO> dtoList = new ArrayList<>();
        for(Menu menu : this.menuRepository.findMenuByCatererId(catererId)){
            if (budget.compareTo(menu.getCostPerPax()) >= 0) { // budget is greater than or equal to cost
                mapToGetMenuDTO(dtoList, menu);
            }
        }
        return dtoList;
    }

    // if both params provided
    public List<GetMenuDTO> getMenusForPaginated(Long catererId, int noOfPax, BigDecimal budget){
        List<GetMenuDTO> dtoList = new ArrayList<>();
        for(Menu menu : this.menuRepository.findMenuByCatererId(catererId)){
            if ( noOfPax >= menu.getMinimumPax() && (budget.compareTo(menu.getCostPerPax()) >= 0) ) {
                mapToGetMenuDTO(dtoList, menu);
            }
        }
        return dtoList;
    }

    // helper method to map to GetMenuDTO
    private void mapToGetMenuDTO(List<GetMenuDTO> dtoList, Menu menu) {
        GetMenuDTO dto = new GetMenuDTO();
        dto.setMenuId(menu.getMenuId());
        dto.setMenuName(menu.getMenuName());
        dto.setMinimumPax(menu.getMinimumPax());
        dto.setNumOfCourses(menu.getNoOfCourses());
        dto.setCostPerPax(menu.getCostPerPax());
        dtoList.add(dto);
    }

}
