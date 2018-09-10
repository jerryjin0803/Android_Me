/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;


// This fragment displays all of the AndroidMe images in one large list
// The list appears as a grid of images
// 此 fragment 在一个 GridView 中展示了所有的图片 （ 头、身、腿）
public class MasterListFragment extends Fragment {

    // 声明一个接口 OnImageClickListener 用于在宿主 activity 中触发 callback
    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnImageClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    // 定义接口 OnImageClickListener 调用宿主activity（MainActivity.java）中的 onImageSelected 方法。
    public interface OnImageClickListener {
        void onImageSelected(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    // 复写 onAttach 确保容器activity 实现了接口 OnImageClickListener 否则就抛异常
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        // 为了确保宿主activity 实现了回调接口 OnImageClickListener。
        // 当前 fragment 挂载时强转宿主activity，如果成功表示宿主实现了接口，否则就抛异常
        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    // fragment 相当于太监，定然要有个空荡荡的构造。因为大内总管还会调用它这个空构造。
    public MasterListFragment() {
    }

    // Inflates the GridView of all AndroidMe images
    /*
     * 系统会在片段首次绘制其用户界面时调用此方法。
     * 要想为您的片段绘制 UI，您从此方法中返回的 View 必须是片段布局的根视图。
     * 如果片段未提供 UI，您可以返回 null
     */
    // 我们在此处将所有（头、身、脚）的素材图片放进 GridView 里
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 设置视图布局为 fragment_master_list
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Get a reference to the GridView in the fragment_master_list xml layout file
        // 从布局中获取到 GridView
        GridView gridView = (GridView) rootView.findViewById(R.id.images_grid_view);

        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the image resources to display
        // 创建一个自定义的适配器
        MasterListAdapter mAdapter = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());

        // Set the adapter on the GridView
        // 把适配器设置给 gridView
        gridView.setAdapter(mAdapter);

        // Set a click listener on the gridView and trigger the callback onImageSelected when an item is clicked
        // 在 gridView 上设置单击侦听器，并在单击项目时触发 onImageSelected 回调(实现与 activity 通信)
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Trigger the callback method and pass in the position that was clicked
                // 触发回调方法并传入单击的位置
                mCallback.onImageSelected(position);
            }
        });

        // Return the root view (当前片段的根视图)
        return rootView;
    }

}
