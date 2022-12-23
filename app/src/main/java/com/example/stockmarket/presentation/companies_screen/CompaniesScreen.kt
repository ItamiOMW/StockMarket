package com.example.stockmarket.presentation.companies_screen

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockmarket.R
import com.example.stockmarket.domain.models.Company
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph(start = true)
@Destination
@Composable
fun CompaniesScreen(
    navigator: DestinationsNavigator,
    viewModel: CompaniesViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { query ->
                viewModel.onEvent(CompaniesEvent.OnSearchQueryChange(query))
            },
            placeholder = {
                Text(text = stringResource(R.string.search))
            },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(CompaniesEvent.Refresh)
            },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(count = state.companiesList.size) { i ->
                    val company = state.companiesList[i]
                    CompanyItem(
                        company,
                        Modifier
                            .clickable {
                                //TODO: GO TO DETAIL SCREEN
                            }
                            .padding(16.dp)
                    )
                    if (i < state.companiesList.size) {
                        Divider(modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}