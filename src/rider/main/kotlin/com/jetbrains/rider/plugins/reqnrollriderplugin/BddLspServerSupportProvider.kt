package com.jetbrains.rider.plugins.reqnrollriderplugin

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem

class BddLspServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        if (file.extension == "feature") {
            serverStarter.ensureServerStarted(BddLspServerDescriptor(project))
        }
    }

    override fun createLspServerWidgetItem(
        lspServer: LspServer,
        currentFile: VirtualFile?
    ) =
        LspServerWidgetItem(
            lspServer,
            currentFile,
            icon = ReqnrollIcons.Icons.ReqnrollLogo
        )
}

private class BddLspServerDescriptor(project: Project) : ProjectWideLspServerDescriptor(project, "Foo") {
    override fun isSupportedFile(file: VirtualFile) = file.extension == "feature"
    override fun createCommandLine() = GeneralCommandLine(PathManager.getPluginsPath() + "/reqnrollriderplugin/lsp/BddLspServer.exe")
}