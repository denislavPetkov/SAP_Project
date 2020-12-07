package sap.project.service;

import sap.project.data.enteties.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    void saveClient(Client client);
    Client getClientByID(long id);
    void deleteClientById(long id);

    List<String> getClientsEmails();
}
