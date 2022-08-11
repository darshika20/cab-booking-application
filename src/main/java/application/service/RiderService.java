package application.service;

import application.dto.Rider;
import application.exception.NotFoundException;
import application.mapper.RiderMapper;
import application.repository.RiderRepository;
import application.utility.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RiderService {

    @Autowired
    RiderRepository riderRepository;

    private static final Logger logger = LoggerFactory.getLogger(RiderService.class);

    @Transactional
    public Rider register(Rider rider) {
        if (Validation.notEmptyString(rider.getEmail()) && Validation.notEmptyString(rider.getName())
                && Validation.notEmptyString(rider.getPassword())) {
            if (riderRepository.findById(rider.getEmail()).isPresent()) {
                throw new RuntimeException("This email id is already registered!");
            }
            application.entity.Rider riderEntity = RiderMapper.dtoToEntity(rider);
            riderRepository.save(riderEntity);
            logger.info("Successfully saved rider {} in repository", rider);
            return rider;
        } else {
            throw new RuntimeException("One or more mandatory fields are empty/null");
        }
    }

    public List<Rider> getAllRiders() {
        List<Rider> riders = new ArrayList<>();
        for (application.entity.Rider rider : riderRepository.findAll()) {
            riders.add(RiderMapper.entityToDto(rider));
        }
        return riders;
    }

    public Rider getRiderByEmail(String email) {
        Optional<application.entity.Rider> rider = riderRepository.findById(email);
        if (rider.isEmpty()) {
            throw new RuntimeException("This email Id is not registered in application!");
        } else {
            return RiderMapper.entityToDto(rider.get());
        }
    }
}
