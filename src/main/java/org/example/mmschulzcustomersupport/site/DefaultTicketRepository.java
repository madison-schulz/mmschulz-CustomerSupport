package org.example.mmschulzcustomersupport.site;

import org.example.mmschulzcustomersupport.entities.TicketEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultTicketRepository extends GenericJpaRepository<Long, TicketEntity> implements TicketRepository{

}
