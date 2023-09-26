package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.repository.EspecialistasRepository;
import com.solucoesludicas.mathtrack.repository.MetricasJogoRepository;
import com.solucoesludicas.mathtrack.service.MetricaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalvarMetricaServiceImpl implements MetricaService {

    private final MetricasJogoRepository metricasJogoRepository;
    private final EspecialistasRepository especialistasRepository;

    @Override
    @Transactional
    public void execute(MetricasJogoModel metricasJogoModel) {
        metricasJogoRepository.save(metricasJogoModel);
        var especialista = especialistasRepository.findById(metricasJogoModel.getEspecialistaUUID());
        sendSms(especialista.get().getTelefone());
    }

    public void sendSms(String telefone) {
    Twilio.init("AC75becd98cfb0c5d5a7762ef6207cdf60", "92b85d215d3970fdfbffc668a5114add");
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(telefone),
                        new com.twilio.type.PhoneNumber("+13344224690"),
                        "Novas métricas cadastradas na aplicação.")
                .create();

        System.out.println(message.getSid());
    }

    @Override
    public List<MetricasJogoModel> getAllMetricasByEspecialista(UUID uuid) {
        return metricasJogoRepository.findAllByEspecialistaUUID(uuid);
    }
}
