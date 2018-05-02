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

package uk.ac.ebi.ega.accession.sample;

import uk.ac.ebi.ampt2d.commons.accession.core.BasicAccessioningService;
import uk.ac.ebi.ampt2d.commons.accession.generators.DecoratedAccessionGenerator;
import uk.ac.ebi.ampt2d.commons.accession.hashing.SHA1HashingFunction;
import uk.ac.ebi.ampt2d.commons.accession.persistence.DatabaseService;

public class SampleAccessioningService extends BasicAccessioningService<SampleModel, String, String> {

    public SampleAccessioningService(DecoratedAccessionGenerator<SampleModel, Long> decoratedAccessionGenerator,
                                     DatabaseService<SampleModel, String, String>
                                             dbService) {
        super(
                decoratedAccessionGenerator,
                dbService,
                new SampleModelSummaryFunction(),
                new SHA1HashingFunction()
        );
    }
}
