package com.mapo.eco100.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapo.eco100.config.RetrofitObj
import com.mapo.eco100.entity.comment.Comment
import com.mapo.eco100.service.BoardService
import kotlinx.coroutines.launch

