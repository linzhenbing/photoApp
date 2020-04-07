package com.example.demo.Tools;

import com.jcraft.jsch.*;
import org.omg.CORBA.SystemException;

import java.util.Properties;

public class SFTPUtils {

    private ChannelSftp sftp;
    private Session session;
    private String sftpPath;

    public SFTPUtils() {
        this.connectServer("服务器IP", 22, "用户名", "密码", "需要保存文件的文件夹路径");
    }

    public SFTPUtils(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword, String sftpPath) {
        this.connectServer(ftpHost, ftpPort, ftpUserName, ftpPassword, sftpPath);
    }

    private void connectServer(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword, String sftpPath) {
        try {
            this.sftpPath = sftpPath;

            // 创建JSch对象
            JSch jsch = new JSch();
            // 根据用户名，主机ip，端口获取一个Session对象
            session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
            if (ftpPassword != null) {
                // 设置密码
                session.setPassword(ftpPassword);
            }
            Properties configTemp = new Properties();
            configTemp.put("StrictHostKeyChecking", "no");
            // 为Session对象设置properties
            session.setConfig(configTemp);
            // 设置timeout时间
            session.setTimeout(60000);
            session.connect();
            // 通过Session建立链接
            // 打开SFTP通道
            sftp = (ChannelSftp) session.openChannel("sftp");
            // 建立SFTP通道的连接
            sftp.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开SFTP Channel、Session连接
     */
    public void closeChannel() {
        try {
            if (sftp != null) {
                sftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     *
     * @param localFile  本地文件
     * @param remotePath 远程文件
     * @param fileName   文件名称
     */
    public void upload(String localFile, String remotePath, String fileName) {
        try {
            if (remotePath != null && !"".equals(remotePath)) {
                remotePath = sftpPath + remotePath;
                createDir(remotePath);
                sftp.put(localFile, (remotePath + fileName), ChannelSftp.OVERWRITE);
                sftp.quit();
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param remotePath 远程文件
     * @param fileName   文件名称
     * @param localFile  本地文件
     */
    public void download(String remotePath, String fileName, String localFile) {
        try {
            remotePath = sftpPath + remotePath;
            if (remotePath != null && !"".equals(remotePath)) {
                sftp.cd(remotePath);
            }
            sftp.get((remotePath + fileName), localFile);
            sftp.quit();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param remotePath 要删除文件所在目录
     */
    public void delete(String remotePath) {
        try {
            if (remotePath != null && !"".equals(remotePath)) {
                remotePath = sftpPath + remotePath;
                sftp.rm(remotePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个文件目录
     */
    public void createDir(String createpath) {
        try {
            if (isDirExist(createpath)) {
                this.sftp.cd(createpath);
                return;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())) {
                    sftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }
            }
            this.sftp.cd(createpath);
        } catch (SftpException e) {
//            throw new SystemException("创建路径错误：" + createpath);
        }
    }

    /**
     * 判断目录是否存在
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

}
