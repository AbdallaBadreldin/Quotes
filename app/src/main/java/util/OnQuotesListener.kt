package util

import roomdata.QuotesEntity

interface OnQuotesListener {
        fun setOnButtonQuotesClickListener(listener: OnQuotesListener)
        fun onButtonClick(action: String, quoteEntity: QuotesEntity)

}