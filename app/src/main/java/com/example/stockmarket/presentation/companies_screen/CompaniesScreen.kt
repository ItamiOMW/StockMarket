package com.example.stockmarket.presentation.companies_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockmarket.R
import com.example.stockmarket.presentation.destinations.CompanyInfoScreenDestination
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
            if (state.error == null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(count = state.companiesList.size) { i ->
                        val company = state.companiesList[i]
                        CompanyItem(
                            company,
                            Modifier
                                .clickable {
                                    navigator.navigate(
                                        CompanyInfoScreenDestination(company.symbol)
                                    )
                                }
                                .padding(16.dp)
                        )
                        if (i < state.companiesList.size) {
                            Divider(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column() {
                        Text(
                            text = stringResource(R.string.failed_update_companies),
                            color = MaterialTheme.colors.error
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                            onClick = {
                                viewModel.onEvent(CompaniesEvent.Refresh)
                            },
                            content = { Text(text = stringResource(R.string.retry)) },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                        )
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }
}