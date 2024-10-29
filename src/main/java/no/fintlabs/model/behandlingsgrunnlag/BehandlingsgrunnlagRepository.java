package no.fintlabs.model.behandlingsgrunnlag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.kodeverk.BehandlingsgrunnlagResource;
import no.fintlabs.adapter.events.WriteableResourceRepository;
import no.fintlabs.adapter.models.RequestFintEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BehandlingsgrunnlagRepository implements WriteableResourceRepository<BehandlingsgrunnlagResource> {

    private final BehandlingsgrunnlagResources behandlingsgrunnlagJpaRepository;

    @Override
    public List<BehandlingsgrunnlagResource> getResources() {
        return behandlingsgrunnlagJpaRepository.get();
    }

    @Override
    public List<BehandlingsgrunnlagResource> getUpdatedResources() {
        return new ArrayList<>();
    }

    @Override
    public BehandlingsgrunnlagResource saveResources(BehandlingsgrunnlagResource behandlingsgrunnlagResource, RequestFintEvent requestFintEvent) {
        return null;
    }

}