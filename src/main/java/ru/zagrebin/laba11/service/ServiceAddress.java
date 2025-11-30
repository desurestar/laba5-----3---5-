package ru.zagrebin.laba11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zagrebin.laba11.entity.Address;
import ru.zagrebin.laba11.repository.AddressRepositoryTemplate;

import java.util.List;

@Service
public class ServiceAddress {
    @Autowired
    private AddressRepositoryTemplate addressRepository;

    public List<Address> findAllAddress() {
        return addressRepository.findAll();
    }

    public Address findAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public void SaveAddress(Address address) {
        addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public List<Address> findByCity(String city) {
        return addressRepository.findByCityContainingIgnoreCase(city);
    }

    public List<Address> findByStreet(String street) {
        return addressRepository.findByStreetContainingIgnoreCase(street);
    }
}