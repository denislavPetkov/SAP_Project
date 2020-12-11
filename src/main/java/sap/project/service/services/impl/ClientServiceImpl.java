package sap.project.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sap.project.data.enteties.Client;
import sap.project.data.repositories.ClientRepository;
import sap.project.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public void saveClient(Client client) {
        this.clientRepository.save(client);
    }

    @Override
    public Client getClientByID(long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        Client client = null;
        if(optionalClient.isPresent()) {
            client = optionalClient.get();
        }
        else {
            throw new RuntimeException(" Client not found! ");
        }
        return client;
    }

    @Override
    public void deleteClientById(long id) {
        this.clientRepository.deleteById(id);
    }

    @Override
    public List<String> getClientsEmails() {
        return this.clientRepository.getEmails();
    }

    @Override
    public List<Client> getClientsByRepId(long id) {
        return this.clientRepository.getClientsByRepId(id);
    }

    @Override
    public void updateClientRepIdById(long repId, Long id) {
        this.clientRepository.updateClientRepIdById(id,repId);
    }
}
