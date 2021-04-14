package com.viewnext.suma.service;

import com.viewnext.suma.domain.Suma;
import com.viewnext.suma.domain.SumaResultAttempt;
import com.viewnext.suma.domain.User;
import com.viewnext.suma.event.EventDispatcher;
import com.viewnext.suma.event.SumaSolvedEvent;
import com.viewnext.suma.repository.SumaResultAttemptRepository;
import com.viewnext.suma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
class SumaServiceImpl implements SumaService{

    private final RandomGeneratorService randomGeneratorService;
    private final SumaResultAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final EventDispatcher eventDispatcher;

    @Autowired
    public SumaServiceImpl(final RandomGeneratorService randomGeneratorService,
            final SumaResultAttemptRepository attemptRepository, final UserRepository userRepository,
            final EventDispatcher eventDispatcher){
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public Suma createRandomPlusOperation(){
        final int factorA = randomGeneratorService.generateRandomFactor();
        final int factorB = randomGeneratorService.generateRandomFactor();
        return new Suma(factorA, factorB);
    }

    @Transactional
    @Override
    public SumaResultAttempt checkAttempt(final SumaResultAttempt attempt){
        // Check if the user already exists for that alias
        final Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

        // Avoids 'hack' attempts
        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

        // Check if the attempt is correct
        final boolean isCorrect = attempt.getResultAttempt() == attempt.getSuma().getFactorA() + attempt.getSuma()
                .getFactorB();

        final SumaResultAttempt checkedAttempt = new SumaResultAttempt(user.orElse(attempt.getUser()),
                attempt.getSuma(), attempt.getResultAttempt(), isCorrect);

        // Stores the attempt
        final SumaResultAttempt storedAttempt = attemptRepository.save(checkedAttempt);

        // Communicates the result via Event
        eventDispatcher.send(new SumaSolvedEvent(checkedAttempt.getId(), checkedAttempt.getUser().getId(),
                checkedAttempt.isCorrect()));

        return storedAttempt;
    }

    @Override
    public List<SumaResultAttempt> getStatsForUser(final String userAlias){
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }

    @Override
    public SumaResultAttempt getResultById(final Long resultId){
        return attemptRepository.findById(resultId).orElse(null);
    }

}
