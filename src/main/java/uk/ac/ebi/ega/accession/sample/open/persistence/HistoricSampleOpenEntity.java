/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.ebi.ega.accession.sample.open.persistence;

import uk.ac.ebi.ampt2d.commons.accession.persistence.jpa.entities.InactiveAccessionEntity;
import uk.ac.ebi.ega.accession.sample.open.model.SampleOpen;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HistoricSampleOpenEntity extends InactiveAccessionEntity<SampleOpen, Long> implements SampleOpen {

    @Column(nullable = false)
    private String biosampleAccession;

    HistoricSampleOpenEntity() {
        super();
    }

    public HistoricSampleOpenEntity(SampleOpenEntity entity) {
        super(entity);
        this.biosampleAccession = entity.getBiosampleAccession();
    }

    @Override
    public SampleOpen getModel() {
        return this;
    }

    @Override
    public String getBiosampleAccession() {
        return biosampleAccession;
    }

}
