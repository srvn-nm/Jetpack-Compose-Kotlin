package com.example.roomdb.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdb.ui.screens.converter.TopScreen
import com.example.roomdb.ui.screens.history.HistoryScreen
import com.example.roomdb.viewModel.ConverterViewModel

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    factory: ConverterViewModelFactory,
    converterViewModel: ConverterViewModel = viewModel(factory = factory)
) {
    val list = converterViewModel.getConversions()
    val historyList = converterViewModel.resultList.collectAsState(initial = emptyList())

    Column (
        modifier = modifier
            .padding(30.dp)
    ){
        TopScreen(list){
            message1, message2 ->
            converterViewModel.addResult(message1, message2)
        }
        Spacer(
            modifier = modifier
                .height(20.dp)
        )
        HistoryScreen(
            historyList,{item->
                converterViewModel.removeResult(item)
            },
            {
                converterViewModel.clearAll()
            }
        )
    }
}
