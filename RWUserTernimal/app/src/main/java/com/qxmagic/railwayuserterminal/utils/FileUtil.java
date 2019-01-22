/*
 * 文 件 名:  FileUtil.java
 * 描    述:  文件工具类
 * 创 建 人:  李晨光
 * 创建时间:  2013年7月7日
 */
package com.qxmagic.railwayuserterminal.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 文件工具类
 * 
 * @author  李晨光
 * @version  [版本号, 2013年7月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileUtil
{
    
    /**
     * 创建文件
     * @param path 文件所在目录路径
     * @param fileName 文件名
     * @see [类、类#方法、类#成员]
     */
    public static void createFile(String path, String fileName)
    {
        
        // 创建目录
        ctreateDir(path);
        File file = new File(path + File.separator + fileName);
        if (!fileIsExist(path, fileName))
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    public static void writeFile(InputStream in, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file != null && file.exists())
            file.delete();

        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 128];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();

    }

    /**
     * 创建目录
     * @param path 目录路径
     * @see [类、类#方法、类#成员]
     */
    public static void ctreateDir(String path)
    {
        File file = new File(path);
        if (!dirIsExist(path))
        {
            file.mkdirs();
        }
    }
    
    /**
     * 判断目录是否存在
     * @param path
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean dirIsExist(String path)
    {
        File file = new File(path);
        if (file.isFile() && file.exists())
        {
            return true;
        }
        return false;
    }
    
    /**
     * 判断文件是否存在
     * @param path
     * @param fileName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean fileIsExist(String path, String fileName)
    {
        File file = new File(path + File.separator + fileName);
        if (file.isFile() && file.exists())
        {
            return true;
        }
        return false;
    }
    
    /**
     * 获取文件的二进制流
     * @param filePath 文件路径
     * @return 文件的二进制流
     * @see [类、类#方法、类#成员]
     */
    public static byte[] getFileBytes(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return buffer;
    }
    
    public static boolean isHasMatchFile(File file, String match)
    {
        if (null == file)
        {
            return false;
        }
        
        if (file.isDirectory())
        {
            File[] files = file.listFiles();
            
            if (null != files)
            {
                
                for (File file2 : files)
                {
                    isHasMatchFile(file2, match);
                }
            }
        }
        
        if (file.getName().matches(match))
        {
            return true;
        }
        
        return false;
    }
    
    public static void getAllFiles(File file, String match, List<File> list)
    {
        
        if (null == file)
        {
            return;
        }
        
        if (file.isDirectory())
        {
            File[] files = file.listFiles();
            
            if (null != files)
            {
                for (File file2 : files)
                {
                    getAllFiles(file2, match, list);
                }
            }
            
            return;
        }
        
        if (file.getName().matches(match))
        {
            list.add(file);
        }
        
    }
    
    public static File findFileByName(String fileName, File directory)
    {
        if (null == directory || StringUtil.isEmpty(fileName))
        {
            return null;
        }
        
        if (!directory.exists() || directory.isDirectory())
        {
            return null;
        }
        
        File[] files = directory.listFiles();
        
        if (null == files)
        {
            return null;
        }
        
        for (File file : files)
        {
            if (fileName.equals(file.getName()))
            {
                return file;
            }
        }
        
        return null;
    }
    
    /** <一句话功能简述>
     * <功能详细描述>
     * @param context
     * @param uri
     * @return [参数说明]
     * 获取选择的文件的路径
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getPath(Context context, Uri uri)
    {
        if (uri == null)
        {
            return null;
        }
        if ("content".equalsIgnoreCase(uri.getScheme()))
        {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try
            {
                cursor = context.getContentResolver().query(uri,
                        projection,
                        null,
                        null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst())
                {
                    return cursor.getString(column_index);
                }
            }
            catch (Exception e)
            {
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme()))
        {
            return uri.getPath();
        }
        return null;
    }
    
    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     * @param context
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Context context, Uri imageUri)
    {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, imageUri))
        {
            if (isExternalStorageDocument(imageUri))
            {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type))
                {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            else if (isDownloadsDocument(imageUri))
            {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            else if (isMediaDocument(imageUri))
            {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type))
                {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("video".equals(type))
                {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("audio".equals(type))
                {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context,
                        contentUri,
                        selection,
                        selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme()))
        {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme()))
        {
            return imageUri.getPath();
        }
        return null;
    }
    
    public static String getDataColumn(Context context, Uri uri,
            String selection, String[] selectionArgs)
    {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try
        {
            cursor = context.getContentResolver().query(uri,
                    projection,
                    selection,
                    selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst())
            {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri)
    {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri)
    {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri)
    {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri)
    {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
