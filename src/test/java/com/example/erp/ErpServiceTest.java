package com.example.erp;
import com.example.erp.model.Models.*;import com.example.erp.repo.InventoryRepo;import com.example.erp.repo.TransferRepo;import com.example.erp.repo.OrderRepo;import com.example.erp.repo.WorkRepo;import com.example.erp.service.ErpService;import org.junit.jupiter.api.Test;import org.mockito.*;import java.util.*;import static org.junit.jupiter.api.Assertions.*;import static org.mockito.Mockito.*;
class ErpServiceTest{
@Mock InventoryRepo inv;@Mock TransferRepo tr;@Mock OrderRepo ord;@Mock WorkRepo wo;
ErpService s;ErpServiceTest(){MockitoAnnotations.openMocks(this);s=new ErpService(inv,tr,ord,wo);}
@Test void cannotReserveMoreThanAvailable(){Inventory x=new Inventory();x.physicalQuantity=100;x.reservedQuantity=60;when(inv.lock("A","P","B1")).thenReturn(Optional.of(x));assertThrows(RuntimeException.class,()->s.reserve("A","P","B1","C",50));}
@Test void cannotTransferMoreThanAvailable(){Inventory x=new Inventory();x.item="A";x.location="P";x.batch="B1";x.physicalQuantity=20;Transfer t=new Transfer();t.id=1L;t.item="A";t.sourceLocation="P";t.destinationLocation="M";t.quantity=30;t.status="REQUESTED";when(tr.findById(1L)).thenReturn(Optional.of(t));when(inv.lock("A","P","B1")).thenReturn(Optional.of(x));assertThrows(RuntimeException.class,()->s.dispatch(1L));}
@Test void sameTransferCannotBeReceivedTwice(){Transfer t=new Transfer();t.id=1L;t.status="RECEIVED";t.received=true;when(tr.findById(1L)).thenReturn(Optional.of(t));assertThrows(RuntimeException.class,()->s.receive(1L));}
}
