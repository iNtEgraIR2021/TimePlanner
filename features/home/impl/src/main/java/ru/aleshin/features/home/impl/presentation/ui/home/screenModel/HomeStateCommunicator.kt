/*
 * Copyright 2023 Stanislav Aleshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * imitations under the License.
 */
package ru.aleshin.features.home.impl.presentation.ui.home.screenModel

import ru.aleshin.core.utils.platform.communications.state.StateCommunicator
import ru.aleshin.features.home.impl.presentation.ui.home.contract.HomeViewState
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 18.02.2023.
 */
internal interface HomeStateCommunicator : StateCommunicator<HomeViewState> {

    class Base @Inject constructor() : HomeStateCommunicator,
        StateCommunicator.Abstract<HomeViewState>(defaultState = HomeViewState())
}
