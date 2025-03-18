package com.example.foodcalorieapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

object ImageClassifier {

    private lateinit var interpreter: Interpreter
    private val labels = listOf("苹果", "香蕉", "披萨", "米饭", "鸡肉") // 模型支持的食物标签

    fun initialize(context: Context) {
        // 加载模型
        val model = FileUtil.loadMappedFile(context, "food_classifier.tflite")
        interpreter = Interpreter(model)
    }

    fun classifyImage(imageFile: File): String? {
        // 加载图片并转换为Bitmap
        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath) ?: return null

        // 调整图片大小以匹配模型输入（假设模型输入为224x224）
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

        // 预处理图片
        val tensorImage = TensorImage.fromBitmap(resizedBitmap)
        val byteBuffer = tensorImage.buffer

        // 创建输出缓冲区
        val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, labels.size), org.tensorflow.lite.DataType.FLOAT32)

        // 运行模型推理
        interpreter.run(byteBuffer, outputBuffer.buffer.rewind())

        // 获取分类结果
        val labelOutput = TensorLabel(labels, outputBuffer)
        val result = labelOutput.mapWithFloatValue

        // 找到概率最高的标签
        val bestMatch = result.maxByOrNull { it.value }
        return bestMatch?.key
    }

    fun close() {
        interpreter.close()
    }
}
