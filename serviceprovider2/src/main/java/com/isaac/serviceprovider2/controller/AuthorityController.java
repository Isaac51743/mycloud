package com.isaac.serviceprovider2.controller;

import com.isaac.serviceprovider2.domain.Ticket;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @GetMapping("/vip1")
    @Secured({"ROLE_vip1"})
    public String getResourceForVip1() {
        return "vip1 resource";
    }

    @GetMapping("/vip2")
    @PreAuthorize("hasAuthority('vip2')")
    public String getResourceForVip2() {
        return "vip2 resource";
    }

    @GetMapping("/vip3")
    @PostAuthorize("hasAuthority('vip3')")
    public String getResourceForVip3() {
        System.out.println("PostAuthorize will run after the method!");
        return "vip3 resource";
    }

    @GetMapping("/vip4")
    @PreFilter(value = "filterObject.ticketNumber%2==0")
    public List<Ticket> getResourceForVip4(@RequestBody List<Ticket> ticketList) {
        System.out.println(ticketList);
        return ticketList;
    }

    @GetMapping("/vip5")
    @PostFilter(value = "filterObject.ticketNumber%2==0")
    public List<Ticket> getResourceForVip5() {
        List<Ticket> ticketList = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            ticketList.add(new Ticket(i, "movie name" + i));
        }
        System.out.println(ticketList);
        return ticketList;
    }

    @GetMapping("/limitip")
    public String limitIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
