package com.example.myapplication

import com.example.myapplication.entity.Cart

interface CartItemClickListener {
    fun onItemQuantityPlus(plusPrice: Int)
    fun onItemQuantityMinus(minusPrice: Int)
    fun onItemDelete(cart: Cart)
}
