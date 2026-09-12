package com.example.erp.controller;
import com.example.erp.model.Models.*;import com.example.erp.repo.UserRepo;import com.example.erp.repo.InventoryRepo;import com.example.erp.repo.WorkRepo;import com.example.erp.repo.TransferRepo;import com.example.erp.repo.OrderRepo;import com.example.erp.service.ErpService;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api") public class Api{final UserRepo users;final InventoryRepo inv;final WorkRepo wo;final TransferRepo tr;final OrderRepo ord;final ErpService s;Api(UserRepo u,InventoryRepo i,WorkRepo w,TransferRepo t,OrderRepo o,ErpService s){users=u;inv=i;wo=w;tr=t;ord=o;this.s=s;}
@GetMapping("/me") public Map<String,String> me(java.security.Principal p){return users.findByUsername(p.getName()).map(u->Map.of("username",u.username,"role",u.role,"location",u.location==null?"":u.location)).orElse(Map.of("username",p.getName(),"role","","location",""));}
@GetMapping("/inventory") public List<Inventory> inventory(){return inv.findAll();}
@PostMapping("/inventory") @PreAuthorize("hasRole('OPERATIONS_USER') or hasRole('ADMIN')") public Inventory add(@RequestBody Inventory x){if(x.physicalQuantity<0||x.reservedQuantity<0)throw new IllegalArgumentException("Negative quantity");x.version=0;return inv.save(x);}
@GetMapping("/work-orders") public List<WorkOrder> work(){return wo.findAll();}
@PostMapping("/work-orders") @PreAuthorize("hasRole('ADMIN')") public WorkOrder work(@RequestBody WorkOrder x){return s.saveWork(x);}
@GetMapping("/transfers") public List<Transfer> transfers(){return tr.findAll();}
@PostMapping("/transfers") @PreAuthorize("hasRole('OPERATIONS_USER') or hasRole('ADMIN')") public Transfer transfer(@RequestBody Transfer x){return s.createTransfer(x);}
@PostMapping("/transfers/{id}/dispatch") @PreAuthorize("hasRole('OPERATIONS_USER') or hasRole('ADMIN')") public Transfer dispatch(@PathVariable Long id){return s.dispatch(id);}
@PostMapping("/transfers/{id}/receive") @PreAuthorize("hasRole('OPERATIONS_USER') or hasRole('ADMIN')") public Transfer receive(@PathVariable Long id){return s.receive(id);}
@GetMapping("/orders") public List<CustomerOrder> orders(){return ord.findAll();}
@PostMapping("/orders/reserve") @PreAuthorize("hasRole('SALES_USER') or hasRole('ADMIN')") public CustomerOrder reserve(@RequestBody Map<String,Object> m){return s.reserve((String)m.get("item"),(String)m.get("location"),String.valueOf(m.getOrDefault("batch","B1")),(String)m.get("customer"),((Number)m.get("quantity")).intValue());}}
