package org.example.mmschulzcustomersupport.site;

import org.example.mmschulzcustomersupport.entities.Attachment;

public interface AttachmentRepository extends GenericRepository<Long, Attachment>{

    Attachment getByTicketId(Long ticketId);
}
