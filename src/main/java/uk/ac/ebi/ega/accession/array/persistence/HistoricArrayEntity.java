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
package uk.ac.ebi.ega.accession.array.persistence;

import uk.ac.ebi.ampt2d.commons.accession.persistence.jpa.entities.InactiveAccessionEntity;
import uk.ac.ebi.ega.accession.array.model.Array;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HistoricArrayEntity extends InactiveAccessionEntity<Array, Long> implements Array {

    @Column(nullable = false)
    private String submissionAccount;

    @Column(nullable = false)
    private String alias;

    HistoricArrayEntity() {
        super();
    }

    public HistoricArrayEntity(ArrayEntity entity) {
        super(entity);
        this.submissionAccount = entity.getSubmissionAccount();
        this.alias = entity.getAlias();
    }

    @Override
    public Array getModel() {
        return this;
    }

    @Override
    public String getSubmissionAccount() {
        return submissionAccount;
    }

    @Override
    public String getAlias() {
        return alias;
    }
}
