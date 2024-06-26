package io.microServices.tickets.controllers;

import io.microServices.tickets.dto.TicketCreateDTO;
import io.microServices.tickets.dto.TicketReadDTO;
import io.microServices.tickets.services.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/tickets")
@Api(value = "Ticket Management System")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    @ApiOperation(value = "Create a new ticket")
    public ResponseEntity<TicketReadDTO> createTicket(@RequestBody TicketCreateDTO ticketCreateDTO) {
        return new ResponseEntity<>(ticketService.createTicket(ticketCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "View a list of all tickets")
    public ResponseEntity<List<TicketReadDTO>> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "Get a ticket by its user id")
    public ResponseEntity<List<TicketReadDTO>> getTicketByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(ticketService.getTicketByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a ticket by its id")
    public ResponseEntity<TicketReadDTO> getTicketById(@PathVariable Integer id) {
        return new ResponseEntity<>(ticketService.getTicketById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a ticket")
    public ResponseEntity<TicketReadDTO> updateTicket(@PathVariable Integer id, @RequestBody TicketCreateDTO ticketCreateDTO) {
        return new ResponseEntity<>(ticketService.updateTicket(id, ticketCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a ticket")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/status/{status}")
    @ApiOperation(value = "Update a ticket status")
    public ResponseEntity<TicketReadDTO> updateTicketStatus(@PathVariable Integer id, @PathVariable String status) {
        return new ResponseEntity<>(ticketService.updateTicketStatus(id, status), HttpStatus.OK);
    }
}
