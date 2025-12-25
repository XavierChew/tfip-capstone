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
    // search page (no filters)
    public List<GetMenuDTO> getMenusForPaginated(Long catererId){
        List<GetMenuDTO> dtoList = new ArrayList<>();

        for(Menu menu : this.menuRepository.findMenuByCatererId(catererId)){
            mapToGetMenuDTO(dtoList, menu);
        }
        return dtoList;
    }

    // all caterers page (many many filters)
    public List<GetMenuDTO> getMenusForPaginated(Long catererId, int noOfPax, BigDecimal budget){
        List<GetMenuDTO> dtoList = new ArrayList<>();

        // both not provided
        if ((noOfPax == -1) && (Objects.equals(budget, BigDecimal.valueOf(9999))) ) {
            for(Menu menu : this.menuRepository.findMenuByCatererId(catererId)){
                mapToGetMenuDTO(dtoList, menu);
            }
        }
        // if noOfPax provided
        else if (Objects.equals(budget, BigDecimal.valueOf(9999)) ) {
            for(Menu menu : this.menuRepository.findMenuByCatererId(catererId)) {
                if (noOfPax >= menu.getMinimumPax()) {
                    mapToGetMenuDTO(dtoList, menu);
                }
            }
        }
        // if budget provided
        else if (noOfPax == -1) {
            for(Menu menu : this.menuRepository.findMenuByCatererId(catererId)){
                if (budget.compareTo(menu.getCostPerPax()) >= 0) { // budget is greater than or equal to cost
                    mapToGetMenuDTO(dtoList, menu);
                }
            }
        // both params provided
        } else {
            for(Menu menu : this.menuRepository.findMenuByCatererId(catererId)){
                if ( noOfPax >= menu.getMinimumPax() && (budget.compareTo(menu.getCostPerPax()) >= 0) ) {
                    mapToGetMenuDTO(dtoList, menu);
                }
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
