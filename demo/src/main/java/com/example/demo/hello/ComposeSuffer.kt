package com.example.demo.hello

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.base.config.ActivityConfig
import com.example.demo.R
import com.example.demo.hello.ui.Index

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun rootSuffer(config:ActivityConfig) {

    /**
     * surface可以定义画布大小代替限定符适配
     */
    Surface(modifier = Modifier
        .width(375.dp)
        .fillMaxHeight()) {
        BottomView(config)

    }
}




@Composable
fun AppCenter(modifier: Modifier) {

}


@Composable
fun Hot(modifier: Modifier) {

}

@Composable
fun MySelf(modifier: Modifier) {

}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun BottomView(config:ActivityConfig) {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(CourseTabs.HOME_PAGE) }
    val tabs = CourseTabs.values()
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = colorResource(id = R.color.border),
        bottomBar = {
            BottomNavigation(modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()) {
                tabs.forEach { tab ->
                    BottomNavigationItem(selected = tab == selectedTab,
                        onClick = { setSelectedTab(tab) },
                        icon = {
                            Icon(tab.icon, contentDescription = null)
                        },
                        label = {
                            Text(stringResource(tab.title).toUpperCase())
                        })
                }

            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when (selectedTab) {
            CourseTabs.HOME_PAGE -> Index(modifier,config = config)
            CourseTabs.PROJECT -> AppCenter(modifier)
            CourseTabs.OFFICIAL_ACCOUNT -> Hot(modifier)
            CourseTabs.MINE -> MySelf(modifier)
        }

    }

}

enum class CourseTabs(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    HOME_PAGE(R.string.home_page, Icons.Filled.Favorite),
    PROJECT(R.string.project, Icons.Filled.Favorite),
    OFFICIAL_ACCOUNT(R.string.official_account, Icons.Filled.Favorite),
    MINE(R.string.mine, Icons.Filled.Favorite)
}
