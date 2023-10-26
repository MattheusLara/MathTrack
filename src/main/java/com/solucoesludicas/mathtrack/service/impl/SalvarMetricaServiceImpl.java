package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.models.MetricasCompletasModel;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.models.UserRole;
import com.solucoesludicas.mathtrack.repository.CriancasRepository;
import com.solucoesludicas.mathtrack.repository.JogosRepository;
import com.solucoesludicas.mathtrack.repository.MetricasJogoRepository;
import com.solucoesludicas.mathtrack.repository.UserRepository;
import com.solucoesludicas.mathtrack.service.MetricaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalvarMetricaServiceImpl implements MetricaService {

    private final MetricasJogoRepository metricasJogoRepository;

    private final UserRepository repository;

    private final CriancasRepository criancasRepository;

    private final JogosRepository jogosRepository;

    @Override
    @Transactional
    public void execute(MetricasJogoModel metricasJogoModel) {
        metricasJogoRepository.save(metricasJogoModel);
        var especialista = repository.findByEspecialistaId(metricasJogoModel.getEspecialistaId());
        sendSms(especialista.getTelefone());
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
    public List<MetricasCompletasModel> getAllMetricasByIdentifier(String role, String identificador) {

        var metricas = role.equals(UserRole.ESPECIALISTA.getRole()) ? getAllMetricasByEspecialista(identificador) : getAllMetricasByCrianca(identificador);

        return metricas.stream()
                .map(metricasJogoModel -> {
                    var metricasCompletas = buildMetricasCompletas(metricasJogoModel);

                    return metricasCompletas.orElse(null);
                })
                .collect(Collectors.toList());
    }

    private List<MetricasJogoModel> getAllMetricasByEspecialista(String identificador){
        return metricasJogoRepository.findAllByEspecialistaId(identificador);
    }

    private List<MetricasJogoModel> getAllMetricasByCrianca(String identificador){
        return metricasJogoRepository.findAllByCriancaUUID(UUID.fromString(identificador));
    }

    private Optional<MetricasCompletasModel> buildMetricasCompletas(MetricasJogoModel metricaJogo){
        var crianca = criancasRepository.findById(metricaJogo.getCriancaUUID());
        var jogo = jogosRepository.findById(metricaJogo.getJogoId());

        if (crianca.isPresent() && jogo.isPresent()) {
            return Optional.of(MetricasCompletasModel.builder()
                    .jogoNome(jogo.get().getNome())
                    .tempoSessao(metricaJogo.getTempoSessao())
                    .dificuldadeFase(metricaJogo.getDificuldadeFase())
                    .numeroFase(metricaJogo.getNumeroFase())
                    .criancaNome(crianca.get().getNome())
                    .dataSessao(metricaJogo.getDataSessao())
                    .numeroAcertos(metricaJogo.getNumeroAcertos())
                    .numeroErros(metricaJogo.getNumeroErros())
                    .habilidadeTrabalhada(metricaJogo.getHabilidadeTrabalhada().getValue())
                    .plataforma(metricaJogo.getPlataforma().getValue())
                    .build()
            );
        } else {
            return Optional.empty();
        }
    }
}
