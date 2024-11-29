# Dual Calculator

一個支援橫向雙計算機的 Android 應用程式，支援左右計算機間互相傳遞，並可以透過拖曳調整兩個計算機的大小比例。

## 主要功能

- 基本計算功能 (加減乘除)
- 特殊功能 (正負號切換、百分比計算)
- 支援小數點運算
- 支援連續運算
- 計算結果可在左右計算機間互相傳遞
- 橫向模式下可調整左右計算機大小


![picture or gif url](https://github.com/chen1080430/Dual-Calculator/blob/main/dual-calculator-interview.gif?raw=true)

影片長度: 55 秒

## 核心檔案結構

 app/src/main  
 ┣  java/com/mason/resizecalculator  
 ┃┣  ui/calculator  
 ┃┃┣  CalculatorFeature.kt  
 ┃┃┣  CalculatorViewModel.kt  
 ┃┃┣  CalculatorFragment.kt  
 ┃┃└  ResizableLayout.kt  
 ┃└  MainActivity.kt  
 ┃  
 └  res  
　┣  layout  
　┃┣  calculator_layout.xml  
　┃┣  calculator_switch_layout.xml  
　┃└  fragment_calculator.xml  
　└  layout-land  
　　└  fragment_calculator.xml  


## 核心類別說明

### CalculatorFeature
負責處理計算邏輯：
- 數字輸入處理
- 運算符號處理
- 計算結果產生
- 特殊功能實現（正負號、百分比等）

### CalculatorViewModel
管理計算機狀態：
- 左右計算機數據管理
- 計算結果的即時更新
- 計算機間數據傳遞
- UI 狀態維護

### ResizableLayout
實現可調整大小的佈局：
- 處理觸摸事件
- 動態調整左右計算機比例
- 拖曳邊界處理

## 佈局設計

### 直向模式
- 單一計算機介面
- 標準計算機按鍵配置

### 橫向模式
- 左右雙計算機
- 中間切換區域
- 可拖曳調整大小
- 優化的按鍵佈局

## 測試覆蓋

主要測試檔案：
- `CalculatorFeatureTest.kt`: 測試核心計算邏輯
- `CalculatorViewModelTest.kt`: 測試視圖邏輯

測試範圍包括：
- 基本數字輸入
- 各種運算操作
- 特殊功能操作
- 錯誤處理
- 狀態管理


## Google Play Alpha 版本發佈狀態：審核中 

